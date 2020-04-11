import { getRequest } from '@/scripts/requests';
import { Base64 } from 'js-base64';
import { isUndefinedOrNull } from '@/scripts/util';

export default function downloadFile(data: any): void {
  const url =
    !isUndefinedOrNull(data) &&
    !isUndefinedOrNull(data.item) &&
    !isUndefinedOrNull(data.item.sqlFileName)
      ? `/api/v1/download-sql/${data.item.sqlFileName}`
      : '/api/v1/download-sql/selected';

  getRequest(url)
    .then((response) => response.json())
    .then((file) => {
      const element = document.createElement('a');
      element.setAttribute('href', `data:text/plain;charset=utf-8,${Base64.decode(file.file)}`);
      element.setAttribute('download', file.fileName);
      element.style.display = 'none';
      document.body.appendChild(element);
      element.click();
      document.body.removeChild(element);
    });
}
