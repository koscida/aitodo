import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import useLocalStorage from "../hooks/useLocalStorage";
import { getData, parseISOLocal, updateData } from "../api/CRUD";
import { Box, Checkbox, Paper, Stack } from "@mui/material";
import { styled } from "@mui/material/styles";
import ToDoLists from "../components/ToDoLists";

export default function List() {
	const { id } = useParams();

	const [list, setList] = useLocalStorage(`aitodolist-List-${id}-list`, null);
	const [localLastUpdate, setLocalLastUpdate] = useLocalStorage(
		`aitodolist-List-${id}-localLastUpdate`,
		null
	);
	const [serverLastUpdate, setServerLastUpdate] = useState(null);

	const sortList = (list) =>
		list
			.sort((a, b) => a.itemOrder < b.itemOrder)
			.sort((a, b) => a.isComplete > b.isComplete);

	const pullData = () => {
		const promise = getData(`lists/${id}`);
		promise.then(
			(value) => {
				// console.log("value: ", value);

				// update lists
				let list = value;
				list.items = sortList(value.items);
				// console.log("list: ", list);
				setList(list);

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
		const promise = getData(`lists/${id}/lastUpdate`);
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
		if (list === null || localLastUpdate === null) {
			pullData();
		} else {
			pullLastUpdate();
		}
	};

	const Item = styled(Paper)(({ theme }) => ({
		backgroundColor: theme.palette.mode === "dark" ? "#1A2027" : "#fff",
		...theme.typography.body2,
		padding: theme.spacing(1),
		color: theme.palette.text.secondary,
	}));

	// handlers

	const updateItem = (itemId, item) => {
		const promise = updateData(`lists/${id}/items/${itemId}`, item);
		promise.then(
			(value) => {
				console.log("value: ", value);

				// update lists
				let list = value;
				list.items = sortList(value.items);
				console.log("list: ", list);
				setList(list);

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

	const handleCheck = (e) => {
		console.log(e);
		const {
			target: { name, value },
		} = e;

		updateItem(name, { isComplete: value === "on" });
	};

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
					<ToDoLists />
				</Box>
				<Box>
					{list ? (
						<Stack spacing={1}>
							<h2>{list.listName}</h2>
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
											onChange={handleCheck}
											checked={item.isComplete}
											name={item.itemId.toString()}
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
					) : (
						<></>
					)}
				</Box>
			</Box>
		</>
	);
}
