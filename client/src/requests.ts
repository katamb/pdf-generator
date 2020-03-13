import {BACKEND_URL} from "@/constants";

export async function getRequest(url: string) {
    return await fetch(BACKEND_URL + url, {
        method: 'GET',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json'
        },
    })
}

export async function putRequest(url: string, body: any) {
    return await fetch(BACKEND_URL + url, {
        method: 'PUT',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(body)
    })
}

export async function postRequest(url: string, body: any) {
    return await fetch(BACKEND_URL + url, {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(body)
    })
}
