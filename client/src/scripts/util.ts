import { AUTHORIZATION, ROLE_DEVELOPER, ROLE_EDITOR, ROLES } from '@/scripts/constants';

export function isUndefinedOrNull(element: any): boolean {
  return element === undefined || element === null;
}

export function hasNoContent(element: any): boolean {
  return element === undefined || element === null || element === '';
}

export function getTag(position: string, format: string): string {
  let result = '<';
  if (position === 'END') {
    result += '/';
  }
  switch (format) {
    case 'BOLD':
      result += 'b';
      break;
    case 'ITALIC':
      result += 'i';
      break;
    case 'UNDERLINE':
      result += 'u';
      break;
    case 'STRIKETHROUGH':
      result += 's';
      break;
    default:
      break;
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

export function isCurrentUserDeveloper(): boolean {
  const roles = localStorage.getItem(ROLES);
  if (roles !== null) {
    return roles.includes(ROLE_DEVELOPER);
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
