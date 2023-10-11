import React from "react";
import axios from "axios";

const apiURL = "http://localhost:8080/api";

const buildUrl = (urlEnd) => `${apiURL}/${urlEnd}`;

export function getData(url) {
	url = buildUrl(url);
	console.log("GET data", url);
	return new Promise((res, err) => {
		axios.get(url).then((response) => {
			console.log("response: ", response);
			if (response.status >= 200 && response.status < 300)
				res(response.data);
			else err(response);
		});
	});
}

// export function createData(url, data, resultFunc) {
// 	console.log("CREATE data", data);
// 	makeAxiosCall(url, axiosCREATE, resultFunc);
// }

// const makeAxiosCall = (url, axiosType, resultFunc) => {
// 	let p = axiosType(url);
// 	p.then(
// 		(value) => {
// 			console.log("value: ", value);
// 			resultFunc(value);
// 		},
// 		(error) => {
// 			console.log("error: ", error);
// 		}
// 	);
// };

// const axiosGET = (url) =>
// 	new Promise((res, err) => {
// 		axios.get(url).then((response) => {
// 			console.log("response: ", response);
// 			if (response.status >= 200 && response.status < 300)
// 				res(response.data);
// 			else err(response);
// 		});
// 	});

// const axiosCREATE = (url) =>
// 	new Promise((res, err) => {
// 		axios
// 			.post(url)
// 			.then((response) => {
// 				console.log("response: ", response);
// 				if (response.status >= 200 && response.status < 300)
// 					res(response.data);
// 				else err(response);
// 			})
// 			.catch(function (error) {
// 				console.log(error);
// 			});
// 	});

// export function updateBook(url, bookId, book, setAPIData) {
// 	console.log("UPDATE book", book);
// 	let p = new Promise((res, err) => {
// 		axios
// 			.put(`${apiURL}/${resourceName}/${bookId}`, book)
// 			.then((response) => {
// 				console.log("response: ", response);
// 				if (response.status === 200) res(response.data);
// 				else err(response);
// 			})
// 			.catch(function (error) {
// 				console.log(error);
// 			});
// 	});
// 	p.then(
// 		(value) => {
// 			console.log("value: ", value);
// 			setAPIData(value);
// 		},
// 		(error) => {
// 			console.log("error: ", error);
// 		}
// 	);
// }

// export function deleteBook(url, bookId, deleteCallBack) {
// 	console.log("DELETE bookId: ", bookId);
// 	let p = new Promise((res, err) => {
// 		axios
// 			.delete(`${apiURL}/${resourceName}/${bookId}`)
// 			.then((response) => {
// 				console.log("response: ", response);
// 				deleteCallBack();
// 			})
// 			.catch(function (error) {
// 				console.log(error);
// 			});
// 	});
// }
