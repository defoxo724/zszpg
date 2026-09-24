import Button from 'react-bootstrap/Button'
import ButtonGroup from 'react-bootstrap/ButtonGroup'

interface EntityActionButtonsProps {
	onEdit: () => void
	onDelete: () => void
}

const EntityActionButtons = (props: EntityActionButtonsProps) => (
	<ButtonGroup size="sm">
		<Button variant="outline-primary" onClick={props.onEdit}>
			Edytuj
		</Button>
		<Button variant="outline-danger" onClick={props.onDelete}>
			Usuń
		</Button>
	</ButtonGroup>
)

export default EntityActionButtons
