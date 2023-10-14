import React, { useEffect, useState } from "react";
import useLocalStorage from "../hooks/useLocalStorage";
import { Card, CardContent } from "@mui/material";
import { getData, buildUrl } from "../api/CRUD";

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

	function parseISOLocal(s) {
		var b = s.split(/\D/);
		return new Date(b[0], b[1] - 1, b[2], b[3], b[4], b[5]);
	}

	const pullData = () => {
		const promise = getData("users/1");
		promise.then(
			(value) => {
				console.log("value: ", value);

				// update todo lists
				const toDoLists = value["toDoLists"];
				console.log("toDoLists: ", toDoLists);
				setToDoLists(toDoLists);

				// update local update
				const newLocalLastUpdateDate = new Date();
				console.log("newLocalLastUpdateDate: ", newLocalLastUpdateDate);
				setLocalLastUpdate(newLocalLastUpdateDate);

				// update server update
				const newServerLastUpdate = parseISOLocal(value["lastUpdate"]);
				console.log("newServerLastUpdate: ", newServerLastUpdate);
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
				console.log("newServerLastUpdate: ", newServerLastUpdate);
				setServerLastUpdate(newServerLastUpdate);

				console.log("localLastUpdate: ", localLastUpdate);
				// const localLastUpdateDate = parseISOLocal(localLastUpdate);
				// console.log("localLastUpdateDate: ", localLastUpdateDate);

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
			{toDoLists !== null ? (
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
									{itemsIncomplete} / {list.items.length}{" "}
									Complete
								</p>
							</CardContent>
						</Card>
					);
				})
			) : (
				<>Load Lists</>
			)}
			<p>
				<button onClick={() => loadPage()}>Refresh</button>
			</p>
		</>
	);
};

export default ToDoLists;
