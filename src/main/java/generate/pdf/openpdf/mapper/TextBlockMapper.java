package generate.pdf.openpdf.mapper;

import generate.pdf.openpdf.dto.TextBlockWithStyle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;

import java.util.List;

@Mapper
public interface TextBlockMapper {

    List<TextBlockWithStyle> getTextsByGroupAndLanguage(@Param("printoutType") String printoutType,
                                                        @Param("languageCode") String languageCode);

    TextBlockWithStyle findTextBlockById(@Param("id") Long id);

    String findTextBlockValueById(@Param("id") Long id);

    Long findTextBlockIdByValue(@Param("textBlockValue") String textBlockValue);

    Long findAmountOfTextBlockUsages(@Param("id") Long id);

    List<String> findAllTemplates();

    List<String> findAllLanguagesForTemplate(@Param("template") String template);

    Long updateTemplateToTextTranslation(@Param("textBlockWithStyle") TextBlockWithStyle textBlockWithStyle);

    Long updateTextBlock(@Param("textBlockValue") String textBlockWithStyle,
                         @Param("textBlockId") Long textBlockId);

    void insertTextBlock(@Param("textBlockWithStyle") TextBlockWithStyle textBlockWithStyle);

}
