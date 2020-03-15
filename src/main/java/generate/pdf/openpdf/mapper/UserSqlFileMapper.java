package generate.pdf.openpdf.mapper;

import generate.pdf.openpdf.dto.UserSqlFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserSqlFileMapper {

    List<UserSqlFile> getUserFiles(@Param("username") String username);

    UserSqlFile getSelectedFile(@Param("username") String username);

    void selectFile(@Param("id") Long id);

    void deSelectUserFiles(@Param("username") String username);

    void insertSqlFileReference(UserSqlFile userSqlFile);

}
