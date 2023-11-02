import React, { useRef, useEffect } from "react";
import { TextField } from "@mui/material";

export default function TextInput({
	inputKey,
	inputLabel,
	inputValue,
	inputOnChange,
}) {
	const inputReference = useRef(null);

	// effect
	useEffect(() => {
		inputReference.current.focus();
	}, []);

	// render
	console.log("--render TextInput--");
	return (
		<TextField
			// defaultValue={item ? item.itemDescription : ""}
			key={inputKey}
			label={inputLabel}
			ref={inputReference}
			value={inputValue}
			onChange={inputOnChange}
		/>
	);
}
