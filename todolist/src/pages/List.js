import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import useLocalStorage from "../hooks/useLocalStorage";
import { getData, parseISOLocal, updateData, createData } from "../api/CRUD";
import { Box, Checkbox, Paper, Stack, Button, TextField } from "@mui/material";
import ToDoLists from "../components/ToDoLists";
import { NewReleases } from "@mui/icons-material";
import ListItem from "../components/ListItem";

const isEditingInit = {
	addingItem: false,
	itemId: -1,
};

export default function List() {
	const { id } = useParams();

	// const [list, setList] = useLocalStorage(`aitodolist-List-${id}-list`, null);
	// const [serverLastUpdate, setServerLastUpdate] = useLocalStorage(
	// 	`aitodolist-List-${id}-serverLastUpdate`,
	// 	null
	// );
	const [list, setList] = useState(null);
	const [serverLastUpdate, setServerLastUpdate] = useState(null);

	const [isEditing, setIsEditing] = useState(isEditingInit);

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
				pullList();
			}
			// else console.log("list: ", list);
		}, promiseErrFunc);
	};

	const pullList = () => {
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
			pullList();
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

	////
	// render
	console.log("--render List--");
	return (
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
						<Stack spacing={1} key="stack">
							{list.items.map((item, i) => (
								<ListItem
									itemKey={item.itemId}
									item={item}
									updateItem={updateItem}
									key={`ListItem-${item.itemId}`}
								/>
							))}
						</Stack>
						<ListItem
							itemKey={0}
							key={`ListItem-0`}
							addItem={addItem}
						/>
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
	);
}
