import React, { useEffect, useState } from "react";
import useLocalStorage from "../hooks/useLocalStorage";
import { Card, CardContent } from "@mui/material";
import { getData, buildUrl } from "../api/CRUD";

const testToDoLists = [
	{
		listId: 1,
		listName: "First Test List",
		isComplete: false,
		isDeleted: false,
		items: [
			{
				itemId: 1,
				itemOrder: 1,
				itemDescription: "ABC",
				dueDate: null,
				isComplete: false,
				isDeleted: false,
			},
			{
				itemId: 2,
				itemOrder: 2,
				itemDescription: "XYZ",
				dueDate: null,
				isComplete: false,
				isDeleted: false,
			},
		],
	},
	{
		listId: 2,
		listName: "Second Test List",
		isComplete: false,
		isDeleted: false,
		items: [],
	},
];

const ToDoLists = () => {
	const [toDoLists, setToDoLists] = useLocalStorage(
		"aitodolist-ToDoLists-toDoLists",
		null
	);
	const [localLastUpdate, setLocalLastUpdate] = useLocalStorage(
		"aitodolist-ToDoLists-localLastUpdate",
		null
	);
	const [serverLastUpdate, setServerLastUpdate] = useState(null);

	useEffect(() => {
		// begin
		if (localLastUpdate === null) {
			setLocalLastUpdate(new Date());
		}
		// determine if pull data
		let pullData = false;

		// pull if data null
		if (toDoLists === null) pullData = true;
		else {
			// get server time
			let newServerLastUpdate;
			const promise = getData("users/1/lastUpdate");
			promise.then(
				(value) => {
					console.log("serverLastUpdate: ", value);
					newServerLastUpdate = value;
					setServerLastUpdate(value);
				},
				(error) => {
					console.log("error: ", error);
				}
			);

			// if out of date
			if (localLastUpdate < newServerLastUpdate) pullData = true;
		}

		if (pullData) {
			console.log("pull data");
			const promise = getData("users/1/lists");
			promise.then(
				(value) => {
					setToDoLists(value);
					const currentDate = new Date();
					console.log("localLastUpdate: ", currentDate);
					setLocalLastUpdate(currentDate);
				},
				(error) => {
					console.log("error: ", error);
				}
			);
		}
	}, [toDoLists, localLastUpdate, setToDoLists, setLocalLastUpdate]);

	return toDoLists !== null ? (
		toDoLists.map((list) => {
			const itemsIncomplete = list.items.reduce(
				(sum, item) => sum + (item.isComplete ? 0 : 1),
				0
			);
			return (
				<Card
					sx={{ minWidth: 275, margin: "1rem 0" }}
					key={list.listId}
				>
					<CardContent>
						<p>{list.listName}</p>
						<p>
							{itemsIncomplete} / {list.items.length} Complete
						</p>
					</CardContent>
				</Card>
			);
		})
	) : (
		<>Load Lists</>
	);
};

export default ToDoLists;
