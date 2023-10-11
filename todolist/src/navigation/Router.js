import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import ToDoLists from "../components/ToDoLists";

const Router = () => {
	return (
		<Routes>
			<Route element={<ToDoLists />} path="/" exact={true} />
		</Routes>
	);
};

export default Router;
