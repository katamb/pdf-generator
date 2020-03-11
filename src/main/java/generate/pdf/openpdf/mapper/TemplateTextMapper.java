package generate.pdf.openpdf.mapper;

import generate.pdf.openpdf.dto.ValueTextCombo;
import generate.pdf.openpdf.dto.TemplateTextBlock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TemplateTextMapper {

    /* * * * * *
     * SELECTS *
     * * * * * */

    List<TemplateTextBlock> getTextsByTemplateAndLanguage(
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

    List<String> findAllAvailableTemplates();

    List<ValueTextCombo> findAllLanguagesForTemplate(@Param("template") String template);

    List<ValueTextCombo> findAllAvailableLanguages();

    /* * * * * *
     * UPDATES * - For all updates avoid using auto-generated id's
     * * * * * */

    Long updateTemplateToTextTranslation(@Param("templateTextBlock") TemplateTextBlock templateTextBlock);

    Long updateTextBlock(
            @Param("textBlockValue") String textBlockValue,
            @Param("previousTextBlockValue") String previousTextBlockValue
    );

    /* * * * * *
     * INSERTS * - For all inserts avoid using auto-generated id's
     * * * * * */

    void insertTextBlock(TemplateTextBlock templateTextBlock);

    void batchInsert(@Param("templateTextBlocks") List<TemplateTextBlock> templateTextBlocks);

}
