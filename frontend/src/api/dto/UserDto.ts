import type { User } from '../../types'

export interface LoginUserRequest {
	login: string
	password: string
}

export type LoginUserResponse = User

export interface RegisterNewUserRequest {
	login: string
	password: string
	passwordConfirmation: string
}

export type RegisterNewUserResponse = User

export interface RegistrationFieldErrors {
	login?: string
	password?: string
	passwordConfirmation?: string
}

export interface RegisterNewUserErrorResponse {
	fieldErrors: RegistrationFieldErrors
}

export type GetCurrentSessionResponse = User
