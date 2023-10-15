import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "../pages/Home";
import List from "../pages/List";
import ErrorPage from "./ErrorPage";

const Router = () => {
	return (
		<Routes>
			<Route element={<Home />} path="/" exact={true} />
			<Route element={<List />} path="/list/:id" exact={true} />
			<Route path="*" element={<ErrorPage />} />
		</Routes>
	);
};

export default Router;
