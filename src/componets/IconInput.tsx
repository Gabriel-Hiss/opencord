import Image from "next/image";
import React from "react";

interface IconInputProps {
    type?: string;
    value: string;
    onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    iconPath: string;
    placeholder: string;
}

const IconInput: React.FC<IconInputProps> = ({
    type = "text",
    value,
    onChange,
    iconPath,
    placeholder
}) => (
    <div className="relative w-full">
        <div className="absolute inset-y-0 left-0 flex items-center pl-[13px] pointer-events-none">
            <Image
                src={iconPath}
                alt={placeholder}
                width={21}
                height={21}
                priority
            />
        </div>
        <input
            type={type}
            value={value}
            onChange={onChange}
            className="w-[400px] h-[50px] pl-[40px] pr-4 py-2 rounded-[10px]"
            style={{
                backgroundColor: "#0A0A0A",
                borderColor: "#272727",
                borderWidth: "1px",
                borderStyle: "solid"
            }}
            placeholder={placeholder}
        />
    </div>
);

export default IconInput;

