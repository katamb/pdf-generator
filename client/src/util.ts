export function isUndefinedOrNull(element: any): boolean {
    return element === undefined || element === null;
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
    result += ">";
    return result;
}