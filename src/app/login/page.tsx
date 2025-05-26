"use client";
import TopBar from "@/componets/TopBar";
import {login} from "@/services/authService";
import Image from "next/image";
import IconInput from "@/componets/IconInput";
import { useState } from "react";


export default function Home() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleLogin = async () => {
        if (!email || !password) {
            alert('Please fill in all fields');
            return;
        }

        const jwt = await login(email, password);
        alert(jwt) // need to remove this line on prod
        return;

    }

    return (
        <main className="flex min-h-screen flex-col items-center justify-between p-24 overflow-hidden">
            <TopBar/>

            <div className="flex flex-col items-center justify-center flex-grow space-y-[3.6496vh]">
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
                    onClick={handleLogin}
                    className="w-[175px] h-[50px] rounded-[10px] font-medium text-[16px] bg-[#EDEDED] text-[#191919] hover:bg-[#CCCCCC] transition-colors duration-200">
                    Login
                </button>

            </div>

        </main>
    );
}
