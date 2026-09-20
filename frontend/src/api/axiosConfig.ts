import axios from 'axios'
import { ApplicationConfig } from '../config/ApplicationConfig'

export const axiosConfig = axios.create({
	baseURL: ApplicationConfig.API_URL,
	withCredentials: true,
})
