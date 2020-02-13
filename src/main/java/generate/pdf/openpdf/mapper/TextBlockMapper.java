package generate.pdf.openpdf.mapper;

import generate.pdf.openpdf.dto.TextBlockWithStyle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TextBlockMapper {

    List<TextBlockWithStyle> getTextsByGroupAndLanguage(@Param("printoutType") String printoutType,
                                                        @Param("languageCode") String languageCode);

    TextBlockWithStyle findTextBlockById(@Param("id") long id);
}
