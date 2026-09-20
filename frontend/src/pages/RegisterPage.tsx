import axios from 'axios'
import { useState } from 'react'
import Card from 'react-bootstrap/Card'
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
	const { user, signIn } = useAuth()
	const navigate = useNavigate()
	const [error, setError] = useState<string | null>(null)
	const [fieldErrors, setFieldErrors] = useState<RegistrationFieldErrors>({})

	if (user) {
		return <Navigate to="/meals" replace />
	}

	const handleRegister = async (
		login: string,
		password: string,
		passwordConfirmation: string,
	) => {
		setError(null)
		setFieldErrors({})

		try {
			await UserApi.register(login, password, passwordConfirmation)
			await signIn(login, password)
			navigate('/meals', {
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
		<Card className="auth-card">
			<Card.Body>
				<Card.Title as="h1" className="h3 mb-3">
					Rejestracja
				</Card.Title>
				<StatusMessage message={error} />
				<UserCredentialsForm
					buttonLabel="Utwórz konto"
					onSubmit={handleRegister}
					fieldErrors={fieldErrors}
					showPasswordConfirmation
				/>
				<p className="mt-3 mb-0">
					Masz konto? <Link to="/login">Zaloguj się</Link>
				</p>
			</Card.Body>
		</Card>
	)
}
export default RegisterPage
