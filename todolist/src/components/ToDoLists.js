import React from "react";
import useLocalStorage from "../hooks/useLocalStorage";
import { Card, CardContent } from "@mui/material";

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
	const [toDoListIds, setToDoListIds] = useLocalStorage(
		"aitodolist-toDoListIds",
		testToDoLists
	);

	return testToDoLists ? (
		toDoListIds.map((list) => (
			<Card sx={{ minWidth: 275 }}>
				<CardContent>
					<p>{list.listName}</p>
				</CardContent>
			</Card>
		))
	) : (
		<>Load Lists</>
	);
};

export default ToDoLists;
