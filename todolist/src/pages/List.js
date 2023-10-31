import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import useLocalStorage from "../hooks/useLocalStorage";
import { getData, parseISOLocal, updateData, createData } from "../api/CRUD";
import { Box, Checkbox, Paper, Stack, Button, TextField } from "@mui/material";
import { styled } from "@mui/material/styles";
import ToDoLists from "../components/ToDoLists";
import { NewReleases } from "@mui/icons-material";

export default function List() {
	const { id } = useParams();

	// const [list, setList] = useLocalStorage(`aitodolist-List-${id}-list`, null);
	// const [serverLastUpdate, setServerLastUpdate] = useLocalStorage(
	// 	`aitodolist-List-${id}-serverLastUpdate`,
	// 	null
	// );
	const [list, setList] = useState(null);
	const [serverLastUpdate, setServerLastUpdate] = useState(null);
	const [isAddingItem, setIsAddingItem] = useState(false);
	const [addingItemText, setAddingItemText] = useState("");

	useEffect(() => {
		loadPage();
	}, []);

	////
	// data pulls

	const pullLastUpdate = () => {
		// get server time
		const promise = getData(`lists/${id}/lastUpdate`);
		promise.then((value) => {
			// update server last update
			const newServerLastUpdate = parseISOLocal(value);
			// console.log("newServerLastUpdate: ", newServerLastUpdate);
			setServerLastUpdate(newServerLastUpdate);

			// check if out of date
			if (serverLastUpdate < newServerLastUpdate) {
				pullData();
			}
			// else console.log("list: ", list);
		}, promiseErrFunc);
	};

	const pullData = () => {
		const promise = getData(`lists/${id}`);
		promise.then(processNewListFunc, promiseErrFunc);
	};

	const updateItem = (itemId, itemUpdates) => {
		const promise = updateData(`lists/${id}/items/${itemId}`, itemUpdates);
		promise.then(processNewListFunc, promiseErrFunc);
	};

	const addItem = (itemData) => {
		const promise = createData(`lists/${id}/items`, itemData);
		promise.then(processNewListFunc, promiseErrFunc);
	};

	const processNewListFunc = (value) => {
		// console.log("value: ", value);

		// update list
		let newList = value;
		newList.items = sortList(value.items);
		// console.log("newList: ", newList);
		setList(newList);

		// update server update
		updateServer(value["lastUpdate"]);
	};

	const promiseErrFunc = (error) => {
		console.log("error: ", error);
	};

	////
	// helpers

	const loadPage = () => {
		// begin, if either toDoLists or listUpdate null, pull both
		if (list === null || serverLastUpdate === null) {
			pullData();
		} else {
			pullLastUpdate();
		}
	};

	const updateServer = (value) => {
		const newServerLastUpdate = parseISOLocal(value);
		// console.log("newServerLastUpdate: ", newServerLastUpdate);

		setServerLastUpdate(newServerLastUpdate);
	};

	const sortList = (list) =>
		list
			.sort((a, b) => a.itemOrder > b.itemOrder)
			.sort((a, b) => a.isComplete > b.isComplete);

	////
	// handlers

	const handleCheckItem = (e) => {
		// console.log("e: ", e);
		const {
			target: { checked, id },
		} = e;
		const itemId = Number(id);
		console.log("itemId: ", itemId, ", checked: ", checked);

		const itemUpdates = { itemId, isComplete: checked };

		updateItem(id, itemUpdates);
	};

	const handleShowAddItem = () => {
		setIsAddingItem(true);
	};
	const handleCancelAddItem = () => {
		setIsAddingItem(false);
	};

	const handleSaveAddItem = (e) => {
		const itemOrder = list.items.length + 1;
		const newItem = {
			itemDescription: addingItemText,
			isComplete: false,
			itemOrder,
		};

		addItem(newItem);

		// setIsAddingItem(false);
		setAddingItemText("");
	};

	////
	// components

	const Item = styled(Paper)(({ theme }) => ({
		backgroundColor: theme.palette.mode === "dark" ? "#1A2027" : "#fff",
		...theme.typography.body2,
		padding: theme.spacing(1),
		color: theme.palette.text.secondary,
	}));

	////
	// render

	return (
		<>
			<Box
				sx={{
					display: "grid",
					gridTemplateColumns: "30% 70%",
					gridGap: "1rem",
				}}
			>
				<Box>
					<ToDoLists incomingServerLastUpdate={serverLastUpdate} />
				</Box>
				<Box>
					{list ? (
						<>
							<Box
								sx={{
									display: "flex",
									flexDirection: "row",
									justifyContent: "space-between",
									marginBottom: "1rem",
								}}
							>
								<h2>{list.listName}</h2>
							</Box>
							<Stack spacing={1}>
								{list.items.map((item) => {
									return (
										<Item
											key={item.itemId}
											sx={{
												display: "flex",
												alignItems: "center",
											}}
										>
											<Checkbox
												onChange={handleCheckItem}
												checked={item.isComplete}
												id={item.itemId.toString()}
											/>
											<p>{item.itemDescription}</p>
											{item.dueDate ? (
												<p>{item.dueDate}</p>
											) : (
												<></>
											)}
										</Item>
									);
								})}
							</Stack>
							{isAddingItem ? (
								<Paper
									sx={{ padding: "1rem", margin: "1rem 0" }}
								>
									<div>
										<TextField
											id="newItem"
											label="New Item"
											value={addingItemText}
											onChange={(e) =>
												setAddingItemText(
													e.target.value
												)
											}
											variant="outlined"
											sx={{ width: "100%" }}
										/>
									</div>
									<Box
										sx={{
											display: "flex",
											flexDirection: "row",
											justifyContent: "space-between",
											marginTop: "1rem",
										}}
									>
										<Button
											variant="outline"
											onClick={handleCancelAddItem}
										>
											Cancel
										</Button>
										<Button
											variant="contained"
											onClick={handleSaveAddItem}
										>
											Save
										</Button>
									</Box>
								</Paper>
							) : (
								<Box
									sx={{
										display: "flex",
										flexDirection: "row",
										justifyContent: "end",
										marginTop: "1rem",
									}}
								>
									<Button
										variant="outlined"
										onClick={handleShowAddItem}
									>
										New Item
									</Button>
								</Box>
							)}
						</>
					) : (
						<></>
					)}
					<p>
						serverLastUpdate:{" "}
						{serverLastUpdate
							? serverLastUpdate.toString()
							: "Loading..."}
					</p>
				</Box>
			</Box>
		</>
	);
}
