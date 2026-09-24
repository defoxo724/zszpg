import axios from 'axios'

export const backendClient = axios.create({
	baseURL: import.meta.env.VITE_API_URL ?? 'http://localhost:8080',
	withCredentials: true,
})
