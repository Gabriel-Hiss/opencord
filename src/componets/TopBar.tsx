import Image from "next/image";

export default function TopBar() {
    return (
        <div className="md:h-[10.4275vh] md:px-[1.3541vh] flex items-center fixed top-0 w-full z-50" style={{ backgroundColor: "#0A0A0A", color: "#EDEDED", borderBottomColor: "#2D2D2D", borderBottomWidth: "1px", borderBottomStyle: "solid" }}>
            <Image
                src="/Logo.svg"
                alt="Logo"
                width={99}
                height={99}
                priority
            />
        </div>
    );
}