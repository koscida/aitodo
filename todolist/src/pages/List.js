import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import useLocalStorage from "../hooks/useLocalStorage";
import { getData, parseISOLocal, updateData } from "../api/CRUD";
import { Box, Checkbox, Paper, Stack } from "@mui/material";
import { styled } from "@mui/material/styles";
import ToDoLists from "../components/ToDoLists";

export default function List() {
	const { id } = useParams();

	// const [list, setList] = useLocalStorage(`aitodolist-List-${id}-list`, null);
	// const [serverLastUpdate, setServerLastUpdate] = useLocalStorage(
	// 	`aitodolist-List-${id}-serverLastUpdate`,
	// 	null
	// );
	const [list, setList] = useState(null);
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
				let newList = value;
				newList.items = sortList(value.items);
				console.log("newList: ", newList);
				setList(newList);

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
		const promise = getData(`lists/${id}/lastUpdate`);
		promise.then(
			(value) => {
				// update server last update
				const newServerLastUpdate = parseISOLocal(value);
				// console.log("newServerLastUpdate: ", newServerLastUpdate);
				setServerLastUpdate(newServerLastUpdate);

				// check if out of date
				if (serverLastUpdate < newServerLastUpdate) {
					pullData();
				} else console.log("list: ", list);
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
		if (list === null || serverLastUpdate === null) {
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

	const updateItem = (itemId, itemUpdates) => {
		const promise = updateData(`lists/${id}/items/${itemId}`, itemUpdates);
		promise.then(
			(value) => {
				// console.log("value: ", value);

				// update lists
				let newList = value;
				newList.items = sortList(value.items);
				console.log("newList: ", newList);
				setList(newList);

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

	const handleCheck = (e) => {
		// console.log("e: ", e);
		const {
			target: { checked, id },
		} = e;
		const itemId = Number(id);
		console.log("itemId: ", itemId, ", checked: ", checked);

		const itemUpdates = { itemId, isComplete: checked };

		updateItem(id, itemUpdates);
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
					<ToDoLists incomingServerLastUpdate={serverLastUpdate} />
				</Box>
				<Box>
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
						) : (
							<></>
						)}
					</Box>
					<p>
						serverLastUpdate:{" "}
						{serverLastUpdate
							? serverLastUpdate.toString()
							: "NULL"}
					</p>
				</Box>
			</Box>
		</>
	);
}
