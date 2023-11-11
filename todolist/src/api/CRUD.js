import React from "react";
import axios from "axios";

export function parseISOLocal(s) {
	var b = s.split(/\D/);
	return new Date(b[0], b[1] - 1, b[2], b[3], b[4], b[5]);
}

const apiURL = "http://localhost:8080/api";

const buildUrl = (urlEnd) => `${apiURL}/${urlEnd}`;

const config = {
	crossDomain: true,
	headers: { "Access-Control-Allow-Origin": "*" },
};

export function getData(url) {
	url = buildUrl(url);
	// console.log("GET data", url);

	return new Promise((res, err) => {
		axios.get(url).then((response) => {
			// console.log("response: ", response);
			if (response.status >= 200 && response.status < 300)
				res(response.data);
			else err(response);
		});
	});
}

export function updateData(url, data) {
	url = buildUrl(url);
	// console.log("UPDATE data", url, data);

	return new Promise((res, err) => {
		axios
			.put(url, data, config)
			.then((response) => {
				// console.log("response: ", response);
				if (response.status >= 200 && response.status < 300)
					res(response.data);
				else err(response);
			})
			.catch(function (error) {
				console.log(error);
			});
	});
}

export function createData(url, data) {
	url = buildUrl(url);
	// console.log("CREATE data", url, data);

	return new Promise((res, err) => {
		axios
			.post(url, data, config)
			.then((response) => {
				console.log("response: ", response);
				if (response.status >= 200 && response.status < 300)
					res(response.data);
				else err(response);
			})
			.catch(function (error) {
				console.log(error);
			});
	});
}

export function deleteData(url) {
	url = buildUrl(url);
	console.log("DELETE: ", url);

	return new Promise((res, err) => {
		axios
			.delete(url, config)
			.then((response) => {
				console.log("response: ", response);
				if (response.status >= 200 && response.status < 300)
					res(response.data);
				else err(response);
			})
			.catch(function (error) {
				console.log(error);
			});
	});
}
