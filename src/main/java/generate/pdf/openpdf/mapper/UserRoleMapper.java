package generate.pdf.openpdf.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserRoleMapper {

    List<String> findRolesByUsername(@Param("username") String username);

}
