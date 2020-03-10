package generate.pdf.openpdf.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import generate.pdf.openpdf.exception.BadRequestException;
import lombok.AllArgsConstructor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.postgresql.core.Utils.escapeLiteral;

@Component
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class MybatisInterceptor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(MybatisInterceptor.class);
    private final ObjectMapper objectMapper;
//    private final StartupConfig startupConfig;

//    public MybatisInterceptor() {
//        this.objectMapper = new ObjectMapper();
//    }

    public MybatisInterceptor(StartupConfig startupConfig) {
        this.objectMapper = new ObjectMapper();
//        this.startupConfig = startupConfig;
    }

    // https://www.programcreek.com/java-api-examples/?code=Caratacus/mybatis-plus-mini/mybatis-plus-mini-master/src/main/java/com/baomidou/mybatisplus/plugins/SqlExplainInterceptor.java
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        if (ms.getSqlCommandType() == SqlCommandType.DELETE
                || ms.getSqlCommandType() == SqlCommandType.UPDATE
                || ms.getSqlCommandType() == SqlCommandType.INSERT) {
            Object parameter = invocation.getArgs()[1];

            Map<String, Object> map = objectMapper.convertValue(parameter, Map.class);
            BoundSql boundSql = ms.getBoundSql(parameter);
//            System.out.println(boundSql.getSql());
            List<ParameterMapping> boundParams = boundSql.getParameterMappings();
            Object paramString = null;
            String fableSql = boundSql.getSql();
            for (ParameterMapping param : boundParams) {
                paramString = findMapValue(new LinkedList<>(Arrays.asList(param.getProperty().split("\\."))), map);
                if (paramString instanceof Number) {
                    fableSql = fableSql.replaceFirst("\\?", String.valueOf(paramString));
                } else {
                    // todo if one of the inserted strings contains '?'
                    fableSql = fableSql.replaceFirst("\\?",
                            "'" + escapeLiteral(null, (String) paramString, false).toString() + "'");
                }
            }
            fableSql += "\n\n";
            try {
                Files.write(Paths.get("sql/running_changes.sql"), fableSql.getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                //exception handling left as an exercise for the reader
            }
            System.out.println(fableSql);

        }
        return invocation.proceed();
    }

    private Object findMapValue(LinkedList<String> valueLocationInMap, Map dynamicDataMap) {
        try {
            if (valueLocationInMap.size() == 1) {
                return dynamicDataMap.get(valueLocationInMap.get(0));
            }
            Object temp = valueLocationInMap.get(0);
            valueLocationInMap.removeFirst();
            return findMapValue(valueLocationInMap, (Map) dynamicDataMap.get(temp));
        } catch (NullPointerException e) {
            String message = String.format("Missing value in input: %s.");
            throw new BadRequestException(message);
        }
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties prop) {
    }

}
