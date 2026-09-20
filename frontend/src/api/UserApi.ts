import type { User } from '../types'
import { axiosConfig } from './axiosConfig'

export interface RegistrationFieldErrors {
	login?: string
	password?: string
	passwordConfirmation?: string
}

export interface RegistrationErrorResponse {
	fieldErrors: RegistrationFieldErrors
}

export class UserApi {
	static async login(loginValue: string, password: string) {
		const response = await axiosConfig.post<User>('/users/login', {
			login: loginValue,
			password,
		})
		return response.data
	}

	static async register(loginValue: string, password: string, passwordConfirmation: string) {
		const response = await axiosConfig.post<User>('/users/register', {
			login: loginValue,
			password,
			passwordConfirmation,
		})
		return response.data
	}

	static async logout() {
		const response = await axiosConfig.post<{ message: string }>('/users/logout')
		return response.data
	}

	static async getSession() {
		const response = await axiosConfig.get<User>('/users/session')
		return response.data
	}
}
