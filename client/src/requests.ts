import {BACKEND_URL} from "@/constants";
import eventBus from "@/eventBus";
import router from "@/router";

function isValid(response: any): void {
    const noErrorMessageStatuses = [200, 300, 401];
    if (!noErrorMessageStatuses.includes(response.status)) {
        response.json()
            .then((error: any) => eventBus.$emit('show-error', error.message));
    }
    if (response.status === 401) {
        router.push({path: '/'});
    }
}

export async function getRequest(url: string, validate = true) {
    const response = await fetch(BACKEND_URL + url, {
        method: 'GET',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json'
        },
    });

    if (validate) {
        isValid(response);
    }

    return response;
}

export async function putRequest(url: string, body: any, validate = true) {
    const response =  await fetch(BACKEND_URL + url, {
        method: 'PUT',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(body)
    });

    if (validate) {
        isValid(response);
    }

    return response;
}

export async function postRequest(url: string, body: any, validate = true) {
    const response =  await fetch(BACKEND_URL + url, {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(body)
    });

    if (validate) {
        isValid(response);
    }

    return response;
}
