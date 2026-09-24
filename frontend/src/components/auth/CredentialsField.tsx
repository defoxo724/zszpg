import Form from 'react-bootstrap/Form'

interface CredentialsFieldProps {
	controlId: string
	label: string
	value: string
	error?: string
	type?: 'text' | 'password'
	onChange: (value: string) => void
}

const CredentialsField = (props: CredentialsFieldProps) => (
	<Form.Group className="mb-3" controlId={props.controlId}>
		<Form.Label>{props.label}</Form.Label>
		<Form.Control
			size="sm"
			type={props.type ?? 'text'}
			value={props.value}
			onChange={(event) => props.onChange(event.target.value)}
			isInvalid={Boolean(props.error)}
			required
		/>
		<Form.Control.Feedback type="invalid">{props.error}</Form.Control.Feedback>
	</Form.Group>
)

export default CredentialsField
