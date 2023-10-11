import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { Container } from "@mui/material";
import AppHeader from "./navigation/AppHeader";
import Router from "./navigation/Router";

const App = () => {
	return (
		<BrowserRouter>
			<div>
				<AppHeader />
				<Container maxWidth="sm" sx={{ marginTop: "1rem" }}>
					<div className="main-content">
						<Router />
					</div>
				</Container>
			</div>
		</BrowserRouter>
	);
};

export default App;
