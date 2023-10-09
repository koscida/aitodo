import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import AppHeader from "./components/template/AppHeader";
import ToDoLists from "./components/ToDoLists";

const App = () => {
	return (
		<BrowserRouter>
			<div>
				<AppHeader />
				<div className="main-content">
					<Routes>
						<Route element={<ToDoLists />} path="/" exact={true} />
					</Routes>
				</div>
			</div>
		</BrowserRouter>
	);
};

export default App;
