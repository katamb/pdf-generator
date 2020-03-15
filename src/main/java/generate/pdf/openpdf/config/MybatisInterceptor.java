package generate.pdf.openpdf.config;

import generate.pdf.openpdf.dto.UserSqlFile;
import generate.pdf.openpdf.exception.InternalServerException;
import generate.pdf.openpdf.service.UserFileService;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@Component
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class MybatisInterceptor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(MybatisInterceptor.class);
    private static final List<String> FILES_TO_LISTEN = Collections.singletonList("mapper/TemplateTextMapper.xml");
    private static final List<SqlCommandType> WRITE_TO_FILE_TYPES = Arrays.asList(
            SqlCommandType.DELETE, SqlCommandType.UPDATE, SqlCommandType.INSERT
    );

    private StartupConfig startupConfig;
    private UserFileService userFileService;

    @Lazy
    public MybatisInterceptor(StartupConfig startupConfig, UserFileService userFileService) {
        this.startupConfig = startupConfig;
        this.userFileService = userFileService;
    }

    /**
     * Created with help from
     * https://www.programcreek.com/java-api-examples/?code=Caratacus/mybatis-plus-mini/mybatis-plus-mini-master/src/main/java/com/baomidou/mybatisplus/plugins/SqlExplainInterceptor.java
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        if (WRITE_TO_FILE_TYPES.contains(mappedStatement.getSqlCommandType())
                && FILES_TO_LISTEN.contains(mappedStatement.getResource())) {
            writeOutSql(invocation, mappedStatement);
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties prop) {
        // Not implemented
    }

    private void writeOutSql(Invocation invocation, MappedStatement mappedStatement) throws SQLException {
        Executor executor = (Executor) invocation.getTarget();
        Configuration configuration = mappedStatement.getConfiguration();
        Object parameter = invocation.getArgs()[1];
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        Connection connection = executor.getTransaction().getConnection();
        String sqlStatement = boundSql.getSql();
        StaticSqlSource sqlSource = new StaticSqlSource(configuration, sqlStatement, boundSql.getParameterMappings());

        MappedStatement.Builder builder = new MappedStatement
                .Builder(configuration, "sql_to_file", sqlSource, SqlCommandType.UNKNOWN);
        MappedStatement queryStatement = builder.resultMaps(mappedStatement.getResultMaps())
                .resultSetType(mappedStatement.getResultSetType())
                .statementType(mappedStatement.getStatementType())
                .build();

        DefaultParameterHandler handler = new DefaultParameterHandler(queryStatement, parameter, boundSql);
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            handler.setParameters(statement);
            String sql = statement.toString().split("\\s+", 3)[2] + ";\n\n";
            Principal principal = SecurityContextHolder.getContext().getAuthentication();
            UserSqlFile userSqlFile = userFileService.getSelectedSqlFile(principal);
            Path pathToFile = Paths.get(startupConfig.getSqlDirectory() + userSqlFile.getSqlFileName());
            Files.write(pathToFile, sql.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new InternalServerException(e.getMessage());
        }
    }
}
