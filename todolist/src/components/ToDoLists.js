import React, { useEffect, useState } from "react";
import useLocalStorage from "../hooks/useLocalStorage";
import { Card, CardContent, Button, Box } from "@mui/material";
import { getData, parseISOLocal } from "../api/CRUD";

const ToDoLists = ({ incomingServerLastUpdate }) => {
	// const [toDoLists, setToDoLists] = useLocalStorage(
	// 	"aitodolist-ToDoLists-toDoLists",
	// 	null
	// );
	// const [serverLastUpdate, setServerLastUpdate] = useLocalStorage(
	// 	"aitodolist-ToDoLists-serverLastUpdate",
	// 	null
	// );
	const [toDoLists, setToDoLists] = useState(null);
	const [serverLastUpdate, setServerLastUpdate] = useState(null);

	const pullData = () => {
		const promise = getData("users/1");
		promise.then(
			(value) => {
				// console.log("value: ", value);

				// update todo lists
				const newToDoLists = value["toDoLists"];
				console.log("newToDoLists: ", newToDoLists);
				setToDoLists(newToDoLists);

				// update server update
				const newLastUpdate = parseISOLocal(value["lastUpdate"]);
				// console.log("newLastUpdate: ", newLastUpdate);
				setServerLastUpdate(newLastUpdate);
			},
			(error) => {
				console.log("error: ", error);
			}
		);
	};

	const pullLastUpdate = () => {
		// get server time
		const promise = getData("users/1/lastUpdate");
		promise.then(
			(value) => {
				// update server last update
				const newServerLastUpdate = parseISOLocal(value);
				// console.log("newServerLastUpdate: ", newServerLastUpdate);
				setServerLastUpdate(newServerLastUpdate);

				// check if out of date
				if (serverLastUpdate < newServerLastUpdate) {
					pullData();
				} else console.log("toDoLists: ", toDoLists);
			},
			(error) => {
				console.log("error: ", error);
			}
		);
	};

	useEffect(() => {
		loadPage();
	}, [incomingServerLastUpdate]);

	const loadPage = () => {
		// begin, if either toDoLists or listUpdate null, pull both
		if (
			toDoLists === null ||
			serverLastUpdate === null ||
			serverLastUpdate !== incomingServerLastUpdate
		) {
			pullData();
		} else {
			pullLastUpdate();
		}
	};

	return (
		<>
			<Box sx={{ display: "flex", textAlign: "end" }}>
				<p>
					<Button variant="outlined" onClick={() => loadPage()}>
						Refresh
					</Button>
				</p>
			</Box>
			{toDoLists !== null ? (
				toDoLists.map((list) => {
					const itemsIncomplete = list.items.reduce(
						(sum, item) => sum + (item.isComplete ? 1 : 0),
						0
					);
					return (
						<Card
							sx={{ minWidth: 275, margin: "1rem 0" }}
							key={list.listId}
						>
							<CardContent
								sx={{
									display: "flex",
									justifyContent: "space-between",
									alignItems: "center",
								}}
							>
								<Box>
									<p>{list.listName}</p>
									<p>
										{itemsIncomplete} / {list.items.length}{" "}
										Complete
									</p>
								</Box>
								<Box>
									<Button
										href={`/list/${list.listId}`}
										variant="outlined"
									>
										Open
									</Button>
								</Box>
							</CardContent>
						</Card>
					);
				})
			) : (
				<>Load Lists</>
			)}
			<p>
				serverLastUpdate:{" "}
				{serverLastUpdate ? serverLastUpdate.toString() : "NULL"}
			</p>
		</>
	);
};

export default ToDoLists;
