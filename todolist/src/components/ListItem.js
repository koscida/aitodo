import React, { useState, useRef, useEffect } from "react";
import {
	Box,
	Paper,
	Stack,
	FormControl,
	Checkbox,
	Button,
	TextField,
} from "@mui/material";
import { styled } from "@mui/material/styles";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import dayjs from "dayjs";
import utc from "dayjs/plugin/utc";
import timezone from "dayjs/plugin/timezone";

import TextInput from "./TextInput";

dayjs.extend(utc);
dayjs.extend(timezone);
dayjs.tz.guess();

const addingItemInit = {
	itemId: 0,
	itemDescription: "",
	dueDate: null,
	isComplete: false,
};

// state:
//	-1	new
//	0	view
//	1	edit
export default function ListItem({
	itemKey,
	item,
	updateItem,
	addItem,
	deleteItem,
}) {
	const itemId = item ? item.itemId : 0;
	const [itemState, setItemState] = useState(item ? 0 : -1);
	const [itemUpdates, setItemUpdates] = useState(
		item ? item : addingItemInit
	);

	const itemDescriptionRef = useRef();
	const dueDateRef = useRef();

	////
	// handlers

	// edit an item
	const handleOpenItem = () => {
		// open editing
		setItemState(1);
	};
	const handleCancelItem = () => {
		if (itemState === 1) {
			// clear editing
			setItemUpdates(item);

			// close editing
			setItemState(0);
		} else {
			// clear add new
			setItemUpdates(addingItemInit);
		}
	};

	// check an item
	const handleCheckItem = ({ target: { checked } }) => {
		// update item data
		const itemUpdates = { itemId, isComplete: checked };
		// console.log("itemUpdates: ", itemUpdates);
		updateItem(itemId, itemUpdates);
	};
	// updating item
	const handleSaveItem = () => {
		// date
		const dueDateValue = dueDateRef.current.children[1].children[0].value;
		const dueDate = dueDateValue
			? dayjs.utc(dueDateValue).toISOString()
			: null;
		// create new item
		let newItem = {
			...itemUpdates,
			itemDescription:
				itemDescriptionRef.current.children[1].children[0].value,
			dueDate,
		};

		// editing
		if (itemState === 1) {
			// save the item data
			newItem.itemId = itemId;
			// console.log("itemId: ", itemId, "newItem: ", newItem);
			updateItem(itemId, newItem);

			// close editing
			setItemState(0);
		}
		// adding
		else {
			// save the item data
			newItem.isComplete = false;
			// console.log("newItem: ", newItem);
			addItem(newItem);

			// reset the add item data
			setItemUpdates(addingItemInit);
		}
	};

	// deleting item
	const handleDeleteItem = () => {
		deleteItem(itemId);
	};

	////
	// components

	const Item = styled(Paper)(({ theme }) => ({
		backgroundColor: theme.palette.mode === "dark" ? "#1A2027" : "#fff",
		...theme.typography.body2,
		padding: itemState === -1 ? theme.spacing(2) : theme.spacing(1),
		marginTop: itemState === -1 ? theme.spacing(2) : 0,
		color: theme.palette.text.secondary,
	}));

	const itemElementStyles = { marginLeft: "0.5rem" };

	const dueDate =
		item && item.dueDate ? dayjs(item.dueDate).format("MM/DD/YYYY") : null;

	////
	// render
	return (
		<LocalizationProvider dateAdapter={AdapterDayjs}>
			<Item
				sx={{
					display: "flex",
					alignItems: "center",
					justifyContent: "space-between",
				}}
			>
				<Box
					sx={{
						display: "flex",
						alignItems: "center",
					}}
				>
					{/* Checkbox */}
					{itemState >= 0 ? (
						<Checkbox
							onChange={handleCheckItem}
							checked={item.isComplete}
							disabled={itemState === 1 || item.isComplete}
						/>
					) : (
						<></>
					)}

					{/* Item Description */}
					{itemState === 0 ? (
						<p
							style={{
								textDecoration: item.isComplete
									? "line-through"
									: "none",
							}}
							ref={itemDescriptionRef}
						>
							{item.itemDescription}
						</p>
					) : (
						<TextField
							key={`item-TextInput-${itemKey}`}
							label={"Item Description"}
							defaultValue={itemUpdates.itemDescription}
							ref={itemDescriptionRef}
						/>
					)}
				</Box>
				<Box
					sx={{
						display: "flex",
						alignItems: "center",
					}}
				>
					{itemState === 0 ? (
						<>
							{/* Date */}
							<p
								style={{
									...itemElementStyles,
								}}
							>
								{dueDate}
							</p>

							{/* Edit Button */}
							{!item.isComplete ? (
								<Button
									variant="outlined"
									onClick={handleOpenItem}
									value={itemId}
									sx={itemElementStyles}
								>
									Edit
								</Button>
							) : (
								<></>
							)}

							{/* Delete Button */}
							{item.isComplete ? (
								<Button
									variant="outlined"
									color="error"
									onClick={handleDeleteItem}
									sx={itemElementStyles}
								>
									Delete
								</Button>
							) : (
								<></>
							)}
						</>
					) : (
						<>
							{/* Date Picker */}
							<DatePicker
								label="Due Date"
								defaultValue={
									dueDate ? dayjs.tz(dueDate) : null
								}
								ref={dueDateRef}
								sx={itemElementStyles}
							/>

							{/* Cancel/Clear Button */}
							<Button
								variant="outline"
								onClick={handleCancelItem}
								sx={itemElementStyles}
							>
								{itemState === 1 ? "Cancel" : "Clear"}
							</Button>

							{/* Add/Save Button */}
							<Button
								variant="contained"
								onClick={handleSaveItem}
								sx={itemElementStyles}
							>
								{itemState === 1 ? "Save" : "Add"}
							</Button>
						</>
					)}
				</Box>
			</Item>
		</LocalizationProvider>
	);
}
