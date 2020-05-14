package generate.pdf.openpdf.mapper;

import generate.pdf.openpdf.dto.NewTextBlockDto;
import generate.pdf.openpdf.dto.TemplateTextDto;
import generate.pdf.openpdf.dto.ValueTextComboDto;
import generate.pdf.openpdf.enums.LanguageCode;
import generate.pdf.openpdf.enums.TemplateCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TemplateTextMapper {

    /* * * * * *
     * SELECTS *
     * * * * * */

    List<TemplateTextDto> getTextsByTemplateAndLanguage(
            @Param("templateCode") TemplateCode templateCode,
            @Param("languageCode") LanguageCode languageCode
    );

    TemplateTextDto findTextBlockById(
            @Param("templateCode") TemplateCode templateCode,
            @Param("languageCode") LanguageCode languageCode,
            @Param("id") Long id
    );

    List<TemplateTextDto> findTextBlocksById(@Param("id") Long id);

    String findTextBlockValueById(@Param("id") Long id);

    Long findTextBlockIdByValue(@Param("textBlockValue") String textBlockValue);

    Long findAmountOfTextBlockUsages(@Param("id") Long id);

    List<String> findAllAvailableTemplates();

    List<ValueTextComboDto> findAllLanguagesForTemplate(@Param("templateCode") TemplateCode templateCode);

    ValueTextComboDto findLanguageByCode(@Param("languageCode") LanguageCode languageCode);

    List<ValueTextComboDto> findAllAvailableLanguages();

    /* * * * * *
     * UPDATES * - For all updates avoid using auto-generated id's
     * * * * * */

    Long updateTemplateToTextTranslation(@Param("templateTextDto") TemplateTextDto templateTextDto);

    Long updateAllTemplatesWithGivenText(@Param("templateTextDto") TemplateTextDto templateTextDto);

    /* * * * * *
     * INSERTS * - For all inserts avoid using auto-generated id's
     * * * * * */

    void insertTextBlock(String textBlockValue);

    void batchInsert(@Param("templateTextDtos") List<TemplateTextDto> templateTextDtos);

    void insertTemplateText(@Param("newTextBlockDto") NewTextBlockDto newTextBlockDto);

}
