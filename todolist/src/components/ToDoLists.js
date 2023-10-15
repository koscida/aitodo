import React, { useEffect, useState } from "react";
import useLocalStorage from "../hooks/useLocalStorage";
import { Card, CardContent, Button, Box } from "@mui/material";
import { getData, parseISOLocal } from "../api/CRUD";

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

	const pullData = () => {
		const promise = getData("users/1");
		promise.then(
			(value) => {
				console.log("value: ", value);

				// update todo lists
				const toDoLists = value["toDoLists"];
				// console.log("toDoLists: ", toDoLists);
				setToDoLists(toDoLists);

				// update local update
				const newLocalLastUpdateDate = new Date();
				// console.log("newLocalLastUpdateDate: ", newLocalLastUpdateDate);
				setLocalLastUpdate(newLocalLastUpdateDate);

				// update server update
				const newServerLastUpdate = parseISOLocal(value["lastUpdate"]);
				// console.log("newServerLastUpdate: ", newServerLastUpdate);
				setServerLastUpdate(newServerLastUpdate);
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

				// console.log("localLastUpdate: ", localLastUpdate);

				// check if out of date
				if (localLastUpdate < newServerLastUpdate) {
					pullData();
				}
			},
			(error) => {
				console.log("error: ", error);
			}
		);
	};

	useEffect(() => {
		loadPage();
	}, []);

	const loadPage = () => {
		// begin, if either toDoLists or listUpdate null, pull both
		if (toDoLists === null || localLastUpdate === null) {
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
		</>
	);
};

export default ToDoLists;
