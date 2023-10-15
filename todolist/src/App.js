import React from "react";
import { BrowserRouter } from "react-router-dom";
import { Container } from "@mui/material";
import AppHeader from "./navigation/AppHeader";
import Router from "./navigation/Router";

const App = () => {
	return (
		<BrowserRouter>
			<div>
				<AppHeader />
				<Container sx={{ marginTop: "1rem" }}>
					<div className="main-content">
						<Router />
					</div>
				</Container>
			</div>
		</BrowserRouter>
	);
};

export default App;
