import { AUTHORIZATION, ROLE_EDITOR, ROLES } from '@/scripts/constants';

export function isUndefinedOrNull(element: any): boolean {
  return element === undefined || element === null;
}

export function isEmptyString(element: any): boolean {
  return element === undefined || element === null || element === '';
}

export function getTag(position: string, format: string): string {
  let result = '<';
  if (position === 'END') {
    result += '/';
  }
  if (format === 'BOLD') {
    result += 'b';
  }
  if (format === 'ITALIC') {
    result += 'i';
  }
  if (format === 'UNDERLINE') {
    result += 'u';
  }
  if (format === 'STRIKETHROUGH') {
    result += 's';
  }
  result += '>';
  return result;
}

export function isCurrentUserEditor(): boolean {
  const roles = localStorage.getItem(ROLES);
  if (roles !== null) {
    return roles.includes(ROLE_EDITOR);
  }
  return false;
}

export function removeCredentialsFromLocalStorage(): void {
  localStorage.removeItem(AUTHORIZATION);
  localStorage.removeItem(ROLES);
}

export function getDateForTable(dateTime: string) {
  const date = dateTime.split('T')[0];
  const time = dateTime.split('T')[1].split('.')[0];
  return `${date} ${time}`;
}

export function getButtonText(selected: boolean) {
  return selected ? 'Selected' : 'Select';
}

export function getButtonStyle(selected: boolean) {
  return selected ? 'primary' : 'outline-primary';
}
