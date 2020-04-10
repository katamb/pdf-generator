import { AUTHORIZATION, BACKEND_URL, SHOW_ERROR_EVENT } from '@/scripts/constants';
import eventBus from '@/scripts/eventBus';
import { removeCredentialsFromLocalStorage } from '@/scripts/util';

function isValid(response: any): void {
  const noErrorMessageStatuses = [200, 300, 401, 403];
  if (!noErrorMessageStatuses.includes(response.status)) {
    response.json().then((error: any) => eventBus.$emit(SHOW_ERROR_EVENT, error.message));
  }
  if (response.status === 401 || response.status === 403) {
    removeCredentialsFromLocalStorage();
  }
}

export async function getRequest(url: string, validate = true) {
  const jwt = localStorage.getItem(AUTHORIZATION);
  const response = await fetch(BACKEND_URL + url, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: jwt !== null ? jwt.toString() : ''
    }
  });

  if (validate) {
    isValid(response);
  }

  return response;
}

export async function putRequest(url: string, body: any, validate = true) {
  const jwt = localStorage.getItem(AUTHORIZATION);
  const response = await fetch(BACKEND_URL + url, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      Authorization: jwt !== null ? jwt.toString() : ''
    },
    body: JSON.stringify(body)
  });

  if (validate) {
    isValid(response);
  }

  return response;
}

export async function postRequest(url: string, body: any, validate = true) {
  const jwt = localStorage.getItem(AUTHORIZATION);
  const response = await fetch(BACKEND_URL + url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: jwt !== null ? jwt.toString() : ''
    },
    body: JSON.stringify(body)
  });

  if (validate) {
    isValid(response);
  }

  return response;
}
