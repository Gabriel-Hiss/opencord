const baseURL = "http://localhost:8080";

export async function login(email: string, password: string) {
    if (!email || !password) {
        alert('Please fill in all fields');
        return;
    }

    try {
        const formdata = new FormData();
        formdata.append("email", email);
        formdata.append("password", password);

        const requestOptions = {
            method: "POST",
            body: formdata,
            redirect: "follow",
        };


        // eslint-disable-next-line @typescript-eslint/ban-ts-comment
        // @ts-expect-error
        const response = await fetch(`${baseURL}/users/management/login`, requestOptions)
        if (!response.ok) {
            alert('Login failed');
            return;
        }
        else {
            return response.text();
        }

    } catch {
        alert('Error logging in');
    }
}