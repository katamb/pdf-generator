DELETE FROM pdf_generator.template_text tt
WHERE tt.template_code = 'EDITABLE_FORM_EE'
  AND tt.language_code = 'pl';
