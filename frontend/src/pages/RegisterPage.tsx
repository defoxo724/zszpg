import axios from 'axios'
import { useState } from 'react'
import { Navigate, useNavigate } from 'react-router-dom'
import { UserApi } from '../api/UserApi'
import type { RegisterNewUserErrorResponse, RegistrationFieldErrors } from '../api/dto/UserDto'
import AuthenticationCard from '../components/auth/AuthenticationCard'
import UserCredentialsForm from '../components/auth/UserCredentialsForm'
import StatusMessage from '../components/common/StatusMessage'
import { useAuth } from '../context/AuthContext'
import { showError, showSuccess } from '../utils/sweetAlert'

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
			void showSuccess('Konto zostało utworzone')
			navigate('/meals', {
				replace: true,
			})
		} catch (requestError) {
			if (axios.isAxiosError<RegisterNewUserErrorResponse>(requestError)) {
				const responseFieldErrors = requestError.response?.data.fieldErrors
				if (responseFieldErrors) {
					setFieldErrors(responseFieldErrors)
					return
				}
			}

			const message = 'Nie udało się utworzyć konta.'
			setError(message)
			await showError('Błąd rejestracji', message)
		}
	}

	return (
		<AuthenticationCard
			title="Rejestracja"
			footerText="Masz konto?"
			footerLinkText="Zaloguj się"
			footerLinkTo="/login"
		>
			<StatusMessage message={error} />
			<UserCredentialsForm
				buttonLabel="Utwórz konto"
				onSubmit={handleRegister}
				fieldErrors={fieldErrors}
				showPasswordConfirmation
			/>
		</AuthenticationCard>
	)
}
export default RegisterPage
