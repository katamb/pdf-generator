import {URL} from "@/constants";

export async function getRequest(url: string) {
    // const jwt = localStorage.getItem("JWT");
    return await fetch(URL + url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
            // ,
            // 'Authorization': jwt !== null ? jwt.toString() : ''
        },
    })
}

export async function putRequest(url: string, body: any) {
    //const jwt = localStorage.getItem("JWT");
    return await fetch(URL + url, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
            //,
            //'Authorization': jwt !== null ? jwt.toString() : ''
        },
        body: JSON.stringify(body)
    })
}

export async function postRequest(url: string, body: any) {
    return await fetch(URL + url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(body)
    })
}
