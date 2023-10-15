import React, { useEffect, useState } from "react";
import useLocalStorage from "../hooks/useLocalStorage";
import { Card, CardContent, Button, Box } from "@mui/material";
import { getData, parseISOLocal } from "../api/CRUD";
import ToDoLists from "../components/ToDoLists";

const Home = () => {
	return <ToDoLists />;
};

export default Home;
