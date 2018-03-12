package edu.vt.crest.hr.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Defines a DepartmentEntity used to represent department rows in the database.
 */
@Entity(name = "Department")
@XmlRootElement
public class DepartmentEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@Version
	@Column(name = "version")
	private int version;

	@Column(name = "name")
	private String name;

	@Column(name = "identifier")
	private String identifier;

	//Without adding EAGER an exception is thrown
	@OneToMany(fetch= FetchType.EAGER)
	@JoinColumn(name = "department_id", referencedColumnName = "id")
	private Set<EmployeeEntity> employees;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	public Set<EmployeeEntity> getEmployees() {
		return this.employees;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof DepartmentEntity)) {
			return false;
		}
		DepartmentEntity other = (DepartmentEntity) obj;
		if (id != null) {
			if (!id.equals(other.id)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public void addEmployee(EmployeeEntity employee) {
		this.employees.add(employee);
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (name != null && !name.trim().isEmpty())
			result += "name: " + name;
		if (identifier != null && !identifier.trim().isEmpty())
			result += ", identifier: " + identifier;
		return result;
	}

}