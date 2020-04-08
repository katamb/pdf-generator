export function isUndefinedOrNull(element: any): boolean {
  return element === undefined || element === null;
}

export function isEmptyString(element: any): boolean {
  return element === undefined || element === null || element === "";
}

export function getTag(position: string, format: string): string {
  let result = "<";
  if (position === "END") {
    result += "/";
  }
  if (format === "BOLD") {
    result += "b";
  }
  if (format === "ITALIC") {
    result += "i";
  }
  if (format === "UNDERLINE") {
    result += "u";
  }
  if (format === "STRIKETHROUGH") {
    result += "s";
  }
  result += ">";
  return result;
}

export function isCurrentUserEditor(): boolean {
  const roles = localStorage.getItem("Roles");
  if (roles !== null) {
    return roles.includes("ROLE_EDITOR");
  }
  return false;
}
