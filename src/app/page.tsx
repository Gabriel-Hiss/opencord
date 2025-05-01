import TopBar from "@/componets/TopBar";
import Image from "next/image";

export default function Home() {
    return (
        <main className="flex min-h-screen flex-col items-center justify-between p-24 overflow-hidden">
            <TopBar/>

            <div className="flex flex-col items-center justify-center flex-grow space-y-[3.6496vh]">
                <div className="relative w-full"> {/* email field */}
                    <div className="absolute inset-y-0 left-0 flex items-center pl-[13px] pointer-events-none">
                        <Image
                            src="/mail.svg"
                            alt="mail"
                            width={21}
                            height={21}
                            priority
                        />
                    </div>
                    <input
                        type="text"
                        className="w-[400px] h-[50px] pl-[40px] pr-4 py-2 rounded-[10px]"
                        style={{
                            backgroundColor: "#0A0A0A",
                            borderColor: "#272727",
                            borderWidth: "1px",
                            borderStyle: "solid"
                        }}
                        placeholder="Email"
                    />
                </div>

                <div className="relative w-full"> {/* password field */}
                    <div className="absolute inset-y-0 left-0 flex items-center pl-[13px] pointer-events-none">
                        <Image
                            src="/key.svg"
                            alt="key"
                            width={21}
                            height={21}
                            priority
                        />
                    </div>
                    <input
                        type="password"
                        className="w-[400px] h-[50px] pl-[40px] pr-4 py-2 rounded-[10px]"
                        style={{
                            backgroundColor: "#0A0A0A",
                            borderColor: "#272727",
                            borderWidth: "1px",
                            borderStyle: "solid"
                        }}
                        placeholder="Password"
                    />
                </div>

                <button
                    type="button"
                    className="w-[175px] h-[50px] rounded-[10px] font-medium text-[16px] bg-[#EDEDED] text-[#191919] hover:bg-[#CCCCCC] transition-colors duration-200">
                    Login
                </button>

            </div>

        </main>
    );
}
