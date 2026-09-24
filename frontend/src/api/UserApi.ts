import { backendClient } from '../config/backendClient'
import type {
	GetCurrentSessionResponse,
	LoginUserRequest,
	LoginUserResponse,
	RegisterNewUserRequest,
	RegisterNewUserResponse,
} from './dto/UserDto'

export const UserApi = {
	async login(login: string, password: string): Promise<LoginUserResponse> {
		const request: LoginUserRequest = { login, password }
		const response = await backendClient.post<LoginUserResponse>('/users/login', request)
		return response.data
	},

	async register(
		login: string,
		password: string,
		passwordConfirmation: string,
	): Promise<RegisterNewUserResponse> {
		const request: RegisterNewUserRequest = { login, password, passwordConfirmation }
		const response = await backendClient.post<RegisterNewUserResponse>(
			'/users/register',
			request,
		)
		return response.data
	},

	async logout(): Promise<void> {
		await backendClient.post('/users/logout')
	},

	async getSession(): Promise<GetCurrentSessionResponse> {
		const response = await backendClient.get<GetCurrentSessionResponse>('/users/session')
		return response.data
	},
}
