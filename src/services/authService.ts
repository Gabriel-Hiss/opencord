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
export async function register(email: string, password: string, username: string) {
    if (!email || !password) {
        alert('Please fill in all fields');
        return;
    }

    try {
        const formdata = new FormData();
        formdata.append("email", email);
        formdata.append("password", password);
        formdata.append("username", username)

        const requestOptions = {
            method: "POST",
            body: formdata,
            redirect: "follow",
        };


        // eslint-disable-next-line @typescript-eslint/ban-ts-comment
        // @ts-expect-error
        const response = await fetch(`${baseURL}/users/management/register`, requestOptions)
        if (!response.ok) {
            if (await response.text() != "User already exists") {
                alert('Login failed');
            }
            else {
                alert('User already exists');
            }
            return;
        }
        else if ("User registered successfully" == await response.text()) {
            return login(email, password);
        }

    } catch {
        alert('Error logging in');
    }
}