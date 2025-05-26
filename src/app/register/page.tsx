"use client";
import TopBar from "@/componets/TopBar";
import {register} from "@/services/authService";
import { useState } from "react";
import IconInput from "@/componets/IconInput";

export default function Home() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [username, setUsername] = useState('');

    const handleRegister = async () => {
        if (!email || !password || !username) {
            alert('Please fill in all fields');
            return;
        }

        const jwt = await register(email, password, username);
        if (jwt) {
            alert(jwt)
        }
        return;

    }

    return (
        <main className="flex min-h-screen flex-col items-center justify-between p-24 overflow-hidden">
            <TopBar/>
            <div className="flex flex-col items-center justify-center flex-grow space-y-[3.6496vh]">

                <IconInput
                    type="text"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    iconPath="/person.svg"
                    placeholder="Username"
                />
                <IconInput
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    iconPath="/mail.svg"
                    placeholder="Email"
                />
                <IconInput
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    iconPath="/key.svg"
                    placeholder="Password"
                />
                <button
                    type="button"
                    onClick={handleRegister}
                    className="w-[175px] h-[50px] rounded-[10px] font-medium text-[16px] bg-[#EDEDED] text-[#191919] hover:bg-[#CCCCCC] transition-colors duration-200">
                    Login
                </button>

            </div>

        </main>
    );
}
