import axios from 'axios'
import { useState } from 'react'
import { Link, Navigate, useNavigate } from 'react-router-dom'
import {
	UserApi,
	type RegistrationErrorResponse,
	type RegistrationFieldErrors,
} from '../api/UserApi'
import UserCredentialsForm from '../components/UserCredentialsForm'
import StatusMessage from '../components/StatusMessage'
import { useAuth } from '../context/AuthContext'

const RegisterPage = () => {
	const { user } = useAuth()
	const navigate = useNavigate()
	const [error, setError] = useState<string | null>(null)
	const [fieldErrors, setFieldErrors] = useState<RegistrationFieldErrors>({})

	if (user) {
		return <Navigate to="/meals" replace />
	}

	const handleRegister = async (login: string, password: string) => {
		setError(null)
		setFieldErrors({})

		try {
			await UserApi.register(login, password)
			navigate('/login', {
				replace: true,
			})
		} catch (requestError) {
			if (axios.isAxiosError<RegistrationErrorResponse>(requestError)) {
				const responseFieldErrors = requestError.response?.data.fieldErrors
				if (responseFieldErrors) {
					setFieldErrors(responseFieldErrors)
					return
				}
			}

			setError('Nie udało się utworzyć konta.')
		}
	}

	return (
		<div className="card auth-card">
			<div className="card-body">
				<h1 className="h3 mb-3">Rejestracja</h1>
				<StatusMessage message={error} />
				<UserCredentialsForm
					buttonLabel="Utwórz konto"
					onSubmit={handleRegister}
					fieldErrors={fieldErrors}
				/>
				<p className="mt-3 mb-0">
					Masz konto? <Link to="/login">Zaloguj się</Link>
				</p>
			</div>
		</div>
	)
}
export default RegisterPage
