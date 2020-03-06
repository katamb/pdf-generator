package generate.pdf.openpdf.mapper;

import generate.pdf.openpdf.dto.TemplateTextBlock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TemplateTextMapper {

    List<TemplateTextBlock> getTextsByGroupAndLanguage(
            @Param("templateCode") String templateCode,
            @Param("languageCode") String languageCode
    );

    TemplateTextBlock findTextBlockById(
            @Param("templateCode") String templateCode,
            @Param("languageCode") String languageCode,
            @Param("id") Long id
    );

    String findTextBlockValueById(@Param("id") Long id);

    Long findTextBlockIdByValue(@Param("textBlockValue") String textBlockValue);

    Long findAmountOfTextBlockUsages(@Param("id") Long id);

    List<String> findAllTemplates();

    List<String> findAllLanguagesForTemplate(@Param("template") String template);

    Long updateTemplateToTextTranslation(@Param("templateTextBlock") TemplateTextBlock templateTextBlock);

    Long updateTextBlock(
            @Param("textBlockValue") String templateTextBlock,
            @Param("textBlockId") Long textBlockId
    );

    void insertTextBlock(TemplateTextBlock templateTextBlock);

}
