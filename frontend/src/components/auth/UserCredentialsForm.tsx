import { useState, type FormEvent } from 'react'
import Button from 'react-bootstrap/Button'
import Form from 'react-bootstrap/Form'
import type { RegistrationFieldErrors } from '../../api/dto/UserDto'
import CredentialsField from './CredentialsField'

interface UserCredentialsFormProps {
	buttonLabel: string
	onSubmit: (login: string, password: string, passwordConfirmation: string) => Promise<void>
	fieldErrors?: RegistrationFieldErrors
	showPasswordConfirmation?: boolean
}

const UserCredentialsForm = (props: UserCredentialsFormProps) => {
	const [login, setLogin] = useState('')
	const [password, setPassword] = useState('')
	const [passwordConfirmation, setPasswordConfirmation] = useState('')
	const [submitting, setSubmitting] = useState(false)
	const fieldErrors = props.fieldErrors ?? {}

	const handleSubmit = async (event: FormEvent) => {
		event.preventDefault()
		setSubmitting(true)
		try {
			await props.onSubmit(login, password, passwordConfirmation)
		} finally {
			setSubmitting(false)
		}
	}

	return (
		<Form onSubmit={handleSubmit}>
			<CredentialsField
				controlId="login"
				label="Login"
				value={login}
				error={fieldErrors.login}
				onChange={setLogin}
			/>
			<CredentialsField
				controlId="password"
				label="Hasło"
				type="password"
				value={password}
				error={fieldErrors.password}
				onChange={setPassword}
			/>
			{(props.showPasswordConfirmation ?? false) && (
				<CredentialsField
					controlId="passwordConfirmation"
					label="Powtórz hasło"
					type="password"
					value={passwordConfirmation}
					error={fieldErrors.passwordConfirmation}
					onChange={setPasswordConfirmation}
				/>
			)}
			<Button className="w-100" type="submit" size="sm" disabled={submitting}>
				{submitting ? 'Proszę czekać...' : props.buttonLabel}
			</Button>
		</Form>
	)
}

export default UserCredentialsForm
